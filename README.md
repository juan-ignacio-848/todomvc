# todomvc

A [re-frame](https://github.com/day8/re-frame) application designed to ... well, that part is up to you.

## Development Mode

### Compile css:

Compile css file once.

```
lein garden once
```

Automatically recompile css file on change.

```
lein garden auto
```

### Run application:

```
lein clean
lein dev
```

shadow-cljs will automatically push cljs changes to the browser.

Wait a bit, then browse to [http://localhost:8280](http://localhost:8280).

### Run tests:

Install karma and headless chrome

```
npm install -g karma-cli
```

And then run your tests

```
lein clean
lein run -m shadow.cljs.devtools.cli compile karma-test
karma start --single-run --reporters junit,dots
```

## Production Build

# todomvc
