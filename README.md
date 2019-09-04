# lucenex
去除lucene繁琐的操作，让你专注于实现业务!
# 文档
### 简介：https://gitee.com/Myzhang/luceneplus/blob/master/README.md
### javadoc：https://apidoc.gitee.com/Myzhang/luceneplus
### Demo：https://gitee.com/Myzhang/luceneplus/tree/master/src/test/java/com/ld/lucenex/demo

# 特点
* 原生lucene用法
* 天然支持多数据源
* 自带数据源创建和垃圾回收
* 默认实现近实时索引
* MyDocument 加持 支持 map、json、obj 自动转换
* 一键高亮支持
* 统一的service接口思想
* 注解式声明字段

# 入门
### 创建lucene数据源
```
        new LuceneX(new LuceneXConfig() {
            @Override
            public void configLuceneX(BaseConfig me) {
                me.add("d:/","test",Empty.class);
            }
        });
```

### 定义实体类
```
import com.ld.lucenex.field.FieldKey;
import com.ld.lucenex.field.LDType;

public class Empty{

    @FieldKey(type = LDType.IntPoint)
    private int id;
    @FieldKey(type = LDType.StringField)
    private String name;
    @FieldKey(type = LDType.TextField,highlight = true)
    private String text;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
```
### 添加索引
```
        List<Empty> empties = new ArrayList<>(10);
        for (int i=0;i<10;i++){
            Empty empty = new Empty();
            empty.setId(i);
            empty.setName("新闻");
            empty.setText("8月29日，2019世界人工智能大会在上海开幕，本届大会以“智联世界，无限可能”为主题，展示包括中国在内各国最新的AI产品和技术。在本届大会上，除了各国展出的最新AI技术和产品外，由华为、寒武纪、依图等国内企业自主研发的人工智能芯片组成的AI芯片墙更是受到参观者的关注。图为微软带来了智能菜品识别系统，把食物放在收银检测区，便能自觉识别价格。更厉害的是，系统还能给出营养分析报告，例如热量、脂肪、碳水物、蛋白质等含量。");
            empties.add(empty);
        }
        ServiceImpl<Empty> basisService = ServiceFactory.getService(ServiceImpl.class);
        basisService.addObjects(empties);
```

### 查询索引
```
        ServiceImpl<Empty> basisService = ServiceFactory.getService(ServiceImpl.class);
        Empty searchOne = basisService.searchOne(new TermQuery(new Term("name","百度一下")));
```

### 高亮显示条件
- 字段表明 highlight = true,并且 type = LDType.TextField
```
    @FieldKey(type = LDType.TextField,highlight = true)
    private String text;
```
- 必须调用searchList 并且返回类型是 <T>

### 实时索引条件
- 必须调用ServiceImpl内置的添加/更新删除方法
### 如何自定义 Service 查询接口
```
import com.ld.lucenex.service.ServiceImpl;

public class DemoService<T> extends ServiceImpl<T> {
    public DemoService(String sourceKey) {
        super(sourceKey);
    }
}
```
### 如何与其他web/非web框架集成
* 只要在项目启动的时候 创建lucene数据源即可
# 更新日志
### v2.3
* 新增 FloatDocValuesField 和 DoubleDocValuesField 排序
* 新增 updateObject 和 updateObjects
### v2.2
* 更改BasisService中3个私有方法改为public
* 去除pom.xml中的一个旧的依赖
### v2.1
* 去除一个递归调用接口bug
### v2.0
* 大量改动与之前版本不兼容
* 新增关闭保护，jvm停止前提交所有队列里的数据，保证数据的完整性
* 新增 IndexSource 类代表每一个数据源
* 优化 LuceneX 统一资源管理
* 新增 MyDocument 代替 Object 转 Document 方法
* 新增 ServiceImpl
* 新增 ServiceFactory
* 升级 lucene 为 8.2.0
### v1.4
* 新增 searchOneDoc 方法
* 新增 SimpleExample 用例
### v1.3
* 增加单个对象添加索引<br>
* 同步实时索引<br>
### v1.2
* 增加jfinal、springboot、servlet等集成插件<br>
* 精简代码<br>
* 增加两个实用方法<br>
* 删除2个依赖<br>
* 适配 javabean and map and fastjson 直接添加<br>
* 自动提交&关闭数据、避免丢失数据或手动提交<br>
### v1.1
* 补充大量文档注释<br>
* 精简&优化代码<br>
* 修复一个错误的Demo<br>
### v1.0
* 去除第三方分词依赖&无用的Jar<br>
* 使用cglib 动态代理创建 Service<br>
* 新增拦截器<br>
* 添加更新 默认异步
# 社区
* QQ群号:475349334
# 安装
```
下载后使用Maven打包成jar

mvn clean package -DskipTests
```
