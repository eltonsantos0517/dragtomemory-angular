Grab the files, either by copying the files from the dist folder or (preferably) through bower:

bower install --save angular-gettext
Include the angular-gettext source files in your app:

<script src="bower_components/angular-gettext/dist/angular-gettext.min.js"></script>
Add a dependency to angular-gettext in your Angular app:

angular.module('myApp', ['gettext']);
You can now start using the translate directive to mark strings as translatable.

-- 2

<h1 translate>Hello!</h1>

Adicionar tag translate no .html




App Engine Java Guestbook
Copyright (C) 2010-2012 Google Inc.

## Sample guestbook for use with App Engine Java.

Requires [Apache Maven](http://maven.apache.org) 3.1 or greater, and JDK 7+ in order to run.

To build, run

    mvn package

Building will run the tests, but to explicitly run tests you can use the test target

    mvn test

To start the app, use the [App Engine Maven Plugin](http://code.google.com/p/appengine-maven-plugin/) that is already included in this demo.  Just run the command.

    mvn appengine:devserver

For further information, consult the [Java App Engine](https://developers.google.com/appengine/docs/java/overview) documentation.

To see all the available goals for the App Engine plugin, run

    mvn help:describe -Dplugin=appengine