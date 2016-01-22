multi-security [![build](https://api.travis-ci.org/daggerok/multi-security.svg?branch=master)](https://travis-ci.org/daggerok/multi-security)
==============

**tags**

- spring boot + mustache template engine + context-path resources workaround
- spring security with crypt password storage and csrf protection
- gradle multi project
- curl cli

### checkout sources

```shell
$ git clone https://github.com/daggerok/war-multi-security.git && cd $_
$ ./gradlew build
```

### run spring boot embedded container

```shell
$ java -jar web/build/libs/*.jar
```

### run spring boot using gradle

```shell
$ ./gradlew run
```

### in general...

...we provide login form, but what if some one don't like it?

what if customer wanna use his own form or auth service or whatever... but login is still required

here is a simple example with needed requests description

### login with curl

*I know username, password and I wanna get http://localhost:8080/*

```shell
$ curl -i localhost:8080/
HTTP/1.1 302 Found
...
Location: http://localhost:8080/login
```

*ok, let's get login page*

```shell
$ curl -i localhost:8080/login
HTTP/1.1 200 OK
```

```html
<html><head><title>Login Page</title></head><body onload='document.f.username.focus();'>
<h3>Login with Username and Password</h3><form name='f' action='/login' method='POST'>
<table>
        <tr><td>User:</td><td><input type='text' name='username' value=''></td></tr>
        <tr><td>Password:</td><td><input type='password' name='password'/></td></tr>
        <tr><td colspan='2'><input name="submit" type="submit" value="Login"/></td></tr>
        <input name="_csrf" type="hidden" value="5650f913-2bc2-4458-af4a-c56738a8f5e1" />
</table>
</form></body></html>
```

*understand... username, password and _csrf, let's do login!*

```shell
$ curl -i -XPOST localhost:8080/login -d 'username=max&password=max&_csrf=5650f913-2bc2-4458-af4a-c56738a8f5e1'
HTTP/1.1 302 Found
...
Set-Cookie: JSESSIONID=035BC8261ABC2A919475268D9CE91029; Path=/; HttpOnly
Location: http://localhost:8080/
```

*thank u.. finally I can go and visit needed page..*
    
```shell
$ curl -i localhost:8080/ --cookie 'JSESSIONID=035BC8261ABC2A919475268D9CE91029'
HTTP/1.1 200 OK
```

and i've got my page

```html
<skip/>
    <h2>
        hello, max! (:
    </h2>
    <p>do u know them?</p>
    <ul>
            <li>dag</li>
            <li>bax</li>
    </ul>
<aside>your session id: 035BC8261ABC2A919475268D9CE91029</aside>
<footer>2016 &copy; daggerok</footer>
</div>
</body>
</html>
```

*ok, seems like all fine, bye-bye...*

```shell
$ curl -i -XPOST localhost:8080/logout
```

### custom csrf filter

After adding ouw custom csrf filter (see ```daggerok.multi.web.config.security.CsrfTokenGeneratorFilter```)
and configuring ```daggerok.multi.web.config.security.WebSecurityCfg``` to use it

```java
public class WebSecurityCfg extends WebSecurityConfigurerAdapter {
    @Autowired
    private CsrfTokenGeneratorFilter csrfTokenGeneratorFilter;
    ...
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            ...
            .addFilterAfter(csrfTokenGeneratorFilter, CsrfFilter.class)
            .csrf() // csrf configuration...
```

we can parse _csrf token directly from response

```shell
$ curl -i localhost:8080/login
HTTP/1.1 200 OK
...
X-Frame-Options: SAMEORIGIN
X-CSRF-HEADER: X-CSRF-TOKEN
X-CSRF-PARAM: _csrf
...
X-CSRF-TOKEN: 78d297fa-fae5-48b7-b6c3-c73b17444e59
```

and as we can see from html from response body - tokens are same

```html
<html><head><title>Login Page</title></head><body onload='document.f.username.focus();'>
<h3>Login with Username and Password</h3><form name='f' action='/login' method='POST'>
<table>
        <tr><td>User:</td><td><input type='text' name='username' value=''></td></tr>
        <tr><td>Password:</td><td><input type='password' name='password'/></td></tr>
        <tr><td colspan='2'><input name="submit" type="submit" value="Login"/></td></tr>
        <input name="_csrf" type="hidden" value="78d297fa-fae5-48b7-b6c3-c73b17444e59" />
</table>
</form></body></html>
```

so now for doing login we don't have to pars html at all! all needed information located in response header

let's do login

**1**

```shell
$ curl -i http://localhost:8080/
...
X-CSRF-HEADER: X-CSRF-TOKEN
X-CSRF-PARAM: _csrf
...
X-CSRF-TOKEN: d5b79275-cc44-4088-ba64-f75215483880
Location: http://localhost:8080/login
```

**2**

```shell
$ curl -i -XPOST http://localhost:8080/login -d 'username=max&password=max&_csrf=d5b79275-cc44-4088-ba64-f75215483880'
...
Set-Cookie: JSESSIONID=E16EE9D5C30E8DA09E62F858A9E47223; Path=/; HttpOnly
...
Location: http://localhost:8080/
```

**3**

```shell
$ curl -i http://localhost:8080/ --cookie 'JSESSIONID=E16EE9D5C30E8DA09E62F858A9E47223'
HTTP/1.1 200 OK
```

### do not store decoded passwords, never!

if u are good programmer, then u know - save insecure passwords it's absolutely forbidden. users privacy, MF!

our security config is not exception and of course we are supports this feature too

```java
public class WebSecurityCfg extends WebSecurityConfigurerAdapter {
    ...
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .userDetailsService(userDetailsServiceImpl)
                .passwordEncoder(new BCryptPasswordEncoder());
```

we also used BCryptPasswordEncoder in our PasswordGenerator encoder

so, even if u get not hashed password, you must encode it before save in database, for example as we doing in Initializer

```java
public CommandLineRunner testData(UserRepository userRepository, PasswordGenerator passwordGenerator) {
    return args -> Arrays.asList("max,dag,bax".split(",")).forEach(name ->
            userRepository.save(User.of(name, passwordGenerator.encode(name))));
```

### serving resources with changeable context-path (additional, out of the scope, off topic)

there is a workaround: always use contestPath value in template engine

to fix wrong 404 issue on static content we must provide access to the httpServlerRequest.getContextPath()

*in template (see: ```web/src/main/resources/templates/parts/header.html```)*

```html
<head>
...
<link rel="stylesheet" href="{{springMacroRequestContext.request.contextPath}}/bootstrap.css">
<link rel="stylesheet" href="{{springMacroRequestContext.request.contextPath}}/app.css">
```

*NOTE: in case error occurs, we must override BasicErrorController, (see: ```daggerok.multi.web.config.error.ErrorControllerImpl```)*

nice :)
