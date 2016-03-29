# rxandroid学习文档
.interval用法
interval创建完成Obserable的可以替换timertask

```Java
private Observable<Long> observable = null;
private Subscriber<Long> subscriber = null;
private void startTimer(){
    Timber.d("startTimer");
    observable = Observable.interval(IntervalTime, TimeUnit.SECONDS);
    subscriber = new Subscriber<Long>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(Long aLong) {
            doSomeThing();
        }
    };

    observable.subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(subscriber);
}
private void stopTimmer(){
    if(subscriber!=null && !subscriber.isUnsubscribed()){
        subscriber.unsubscribe();
    }
    subscriber = null;
    observable = null;
}
```

# error操作符-创建直接执行onError的Observable对象
可以用于测试的环境
```Java
trainBackUseCase.updateReception(token, receptionStatus)
        //模拟网络延迟错误，10次以后才正常
        .flatMap(result -> {
            if (postUpdateReceptionStartCount > 10) {
                return Observable.just(result);
            }else{
                return Observable.error(new Exception());
            }
        })
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new CallBackUpdateReceptionStartBack(handlerUpdateReception, presEmqttMessage));
```

# lambda表达式中使用final变量
observable中使用lambda表达式，可以直接使用定义的变量，使用的是final形式
```Java
public void showPostDialog(String title,String content) {
    //title、content作为final变量传进来可以直接用，任何在lamada表达式用的变量都是，可以再函数中定义其他变量也可以直接引入
    Observable.just(1)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(result -> {
                if(progressDialog!=null){
                    //如果存在先取消掉原来的，然后显示新的
                    hidePostDialog();
                }
                progressDialog = ProgressDialog.show(getActivity(), title, content, true, false);
            },throwable -> {
                throwable.printStackTrace();
            });
}
```
