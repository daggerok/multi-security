multi-security [![build](https://api.travis-ci.org/daggerok/multi-security.svg?branch=master)](https://travis-ci.org/daggerok/multi-security)
==============

tags:
- spring boot
- spring security
- gradle multi project
- cli auth login using curl with csrf

### in general...

...we provide login form, but what if some one don't like it?

what if customer wanna use his own form or auth service or whatever... but login is still required

here is a simple example with needed requests description

### login with curl

*I know username, password and I wanna get http://localhost:8080/*

```shell
curl -i localhost:8080/
HTTP/1.1 302 Found
...
Location: http://localhost:8080/login
...
```

*ok, let's get login page*

```shell
curl -i localhost:8080/login
HTTP/1.1 200 OK
...
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
curl -i -XPOST localhost:8080/login -d 'username=max&password=max&_csrf=5650f913-2bc2-4458-af4a-c56738a8f5e1'
HTTP/1.1 302 Found
...
Set-Cookie: JSESSIONID=035BC8261ABC2A919475268D9CE91029; Path=/; HttpOnly
Location: http://localhost:8080/
...
```

*thank u.. finally I can go and visit needed page..*
    
```shell
curl -i localhost:8080/ --cookie 'JSESSIONID=035BC8261ABC2A919475268D9CE91029'
HTTP/1.1 200 OK
...
```

```html
and i've got my page
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
curl -i -XPOST localhost:8080/logout
```
