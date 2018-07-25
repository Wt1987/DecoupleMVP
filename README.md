# DecoupleMVP
<br>###自己写的一个mvp模式，在v和p中间增加了代理层，让两者解藕，并且能过控制view和presenter生命周期的一致性

<br>###网络请求<br>
<br>####使用了retrofit2 + okhttp+ rxjava2 实现了网络请求，添加了如超时，添加公共header，添加公共参数的方式，（这块后续会持续更新。。。。）<br>
<br>###MVP<br>
<br>####自己对mvp的理解就是，将acitivity进行业务上的拆分，然c,变成c + p ,但是为了减少它们自己的耦合，以及有效的控制生命周期，我添加了，Delegate

<br>