# lucenex
基于JDK 1.8 & lucene 8.x 的搜索框架、在不影响原本性能的同时、提升80%的开发效率、降低70%的开发难度！！！
# 文档
### 简介：https://gitee.com/Myzhang/luceneplus/blob/master/README.md
### javadoc：https://apidoc.gitee.com/Myzhang/luceneplus
### Demo：https://gitee.com/Myzhang/luceneplus/tree/master/src/test/java/com/ld/lucenex/demo

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

# 亮点
* 轻 ~ 不足千行
* 简 ~ 零配置项
* 全 ~ 内置丰富
* 易 ~ 入门简单
* 快 ~ 接近原生
* 稳 ~ 坚如磐石
* 展 ~ 插件扩展

# 特点
* 原声Lucene 用法
* 注解式声明字段
* 多库自动切换
* 自带管理资源创建于释放
* 自带高性能近实时索引
* 可持续扩展的Service 接口设计
* 内置丰富常用接口
* 可直接添加Java对象与Map集合 无需手动创建 Document
* 支持操作回退
* 可自定义分词器
* 异常退出数据保护
# 社区
* QQ群号:475349334
# 安装
```
下载后使用Maven打包成jar
```
# 用法
### 1、继承 LuceneXConfig
```
public class DemoConfig extends LuceneXConfig{
	@Override
	public void configConstant(Constants me) {
	}
	@Override
	public void configLuceneX(BaseConfig me) {
		// 存储目录 、名称、高亮、分词器、存储类
		me.add("d:/", "test",  false, new PerFieldAnalyzerWrapper(new StandardAnalyzer()), Empty.class);
	}
}
```
### 2、获取Service
```
//默认使用第一个存储库（如果您只有一个库或使用第一个库的话）
BasisService basisService = LdService.newInstance(BasisService.class);
//自定义使用库
BasisService basisService = LdService.newInstance(BasisService.class,"test");
```
### 3、内置基础方法 满足一般业务
```
public List<Document> TermQuery(String field,String value,int num)
public long IntDelete(String field,int value)
public void addIndex(List<?> list)
public List<Document> searchList(Query query, int n)
public <T> Page<Document> searchList(Query query,Page<Document> page)
public List<Document> searchTotal()
public long addDocuments(Iterable<? extends Iterable<? extends IndexableField>> docs)
public long addDocument(Iterable<? extends IndexableField> doc)
public int count(Query query)
public TopDocs search(Query query, int n)
public TopFieldDocs search(Query query, int n, Sort sort)
public long deleteAll()
public long deleteDocuments
public long deleteDocuments(Term... terms)
public void deleteUnusedFiles()
public long updateIndex(List<Document> list,Term term)
public List<Document> toDocument(List<?> list)
public void goBack()
```
### 4、如何测试
```
//手动启动
LuceneX.start(DemoConfig.class);
```
### 5、如何实现自己的Service
#### 5.1、继承BasisService
#### 5.2、可以使用父类已有的基础方法也可以使用 SourceConfig config 变量获取以下字段
```
private String indexPath;//存储地址
private boolean highlight;//是否高亮
private IndexWriter writer;//写入对象
private IndexSearcher searcher;//查询对象
private PerFieldAnalyzerWrapper analyzer;//分词器
private Class<?> defaultClass;//存储对象Class
private Highlighter highlighter;//高亮标签
```
#### 5.3、使用实例Demo
```
default public int count(Query query) throws IOException {
	return config.getSearcher().count(query);
}
```
