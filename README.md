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
