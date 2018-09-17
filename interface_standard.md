# 接口规范
## 客户端状态码
1. 常用

code | message
--- | ---
0 | 请求成功
-1 | 未知错误

2. 请求合法性

code |  messgae | data
---|---|---
49000 |缺少参数异常 |null
49001 |请求方式不支持 |null
49002 |没有此方法|null
49003 |参数验证失败 |null
49004 |参数绑定失败|null
49005 |参数类型不匹配|null
49006 |JSON_parse_error

3. Token

code | message
--- | ---
49100 | token失效
49101 | token签名错误
49102 | token参数错误
49103 | token解析错误
49104 | token格式错误
49120 | 缺少请求签名SIGN
49121 | 请求签名SIGN错误
49140 | 服务器Redis错误
49141 | 客户端非法

4. 模块

code | message
--- | ---
41000 | 用户模块
42000 | 目录模块
43000 | 文章模块

## 签名生成算法
    APP_ID: 后台提供
    APP_SECRET: 后台提供
签名算法
```$xslt

```

