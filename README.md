# spotlight


## Usage

1. Gradle dependency:

```
allprojects {
   repositories {
      	jcenter()
       	maven { url "https://jitpack.io" }  //Make sure to add this in your project 
   }
}
```
2. and add this

```
implementation 'com.github.ibrahim-mrq:library:1.0.3'

or 

 implementation project(path: ':spotlight')
```
   
<br/>

onCreate
<br/>
```java
 new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                spotlight();
            }
        }, 1000);
        
```

```java
    private void spotlight() {
        SimpleTarget oneTarget = new SimpleTarget.Builder(MainActivity.this)
                // set view
                .setPoint(textView)
                // set zoom
                .setRadius(150f)
                .setTitle("title")
                .setDescription("description")
                .setOnSpotlightStartedListener(new OnTargetStateChangedListener<SimpleTarget>() {
                    @Override
                    public void onStarted(SimpleTarget target) {
                        Toast.makeText(MainActivity.this, "onStarted", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onEnded(SimpleTarget target) {
                        Toast.makeText(MainActivity.this, "onEnded", Toast.LENGTH_SHORT).show();
                    }
                })
                .build();
        SimpleTarget twoTarget = new SimpleTarget.Builder(MainActivity.this)
                // set view
                .setPoint(textView2)
                // set zoom
                .setRadius(150f)
                .setTitle("title")
                .setDescription("description")
                .setOnSpotlightStartedListener(new OnTargetStateChangedListener<SimpleTarget>() {
                    @Override
                    public void onStarted(SimpleTarget target) {
                        Toast.makeText(MainActivity.this, "onStarted", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onEnded(SimpleTarget target) {
                        Toast.makeText(MainActivity.this, "onEnded", Toast.LENGTH_SHORT).show();
                    }
                })
                .build();


        SimpleTarget thirdTarget = new SimpleTarget.Builder(MainActivity.this)
                // set view
                .setPoint(button)
                // set zoom
                .setRadius(150f)
                .setTitle("title")
                .setDescription("description")
                .setOnSpotlightStartedListener(new OnTargetStateChangedListener<SimpleTarget>() {
                    @Override
                    public void onStarted(SimpleTarget target) {
                        Toast.makeText(MainActivity.this, "onStarted", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onEnded(SimpleTarget target) {
                        Toast.makeText(MainActivity.this, "onEnded", Toast.LENGTH_SHORT).show();
                    }
                })
                .build();

        CustomTarget customTarget = new CustomTarget.Builder(MainActivity.this)
                .setPoint(button2)
                .setRadius(80f)
                .setView(R.layout.layout_target)
                .build();


        Spotlight.with(MainActivity.this)
                .setDuration(1000L)
                .setAnimation(new DecelerateInterpolator(2f))
                .setTargets(oneTarget, twoTarget, thirdTarget, customTarget)
                // or
//                .setTargets(oneTarget)
                .setOnSpotlightEndedListener(new OnSpotlightEndedListener() {
                    @Override
                    public void onEnded() {
                        Toast.makeText(MainActivity.this, "onEnded", Toast.LENGTH_SHORT).show();
                    }
                })
                .setOnSpotlightStartedListener(new OnSpotlightStartedListener() {
                    @Override
                    public void onStarted() {
                        Toast.makeText(MainActivity.this, "onStarted", Toast.LENGTH_SHORT).show();
                    }
                })
                .start();
    }

```



