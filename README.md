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
