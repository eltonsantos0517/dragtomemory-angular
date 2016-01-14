Tutorial para traduções disponivel em: https://angular-gettext.rocketeer.be/dev-guide/

Tutorial resumido:

1) Na primeira vez que for utilizar o plugin abrir o terminal na pasta que contiver o arquivo bower.json e rodar os comandos
bower install
npm install grunt-angular-gettext
Instalar tambem o programa POEDIT (http://poedit.net/)


2) Colocar a tag translate aonde se deseja utilizar a tradução 
	Ex: placeholder="{{'Password' | translate}}" ou 
	<translate> Keep me signed in! </translate> ou
	<p translate>Please, enter the email to which the new password will be sent</p>
obs: Para mais informações sobre as tags olhar o tutorial completo.

3) Rodar o comando grunt nggettext_extract, o arquivo template.pot será criado ou atualizado
		
4) Se for a primeira vez que for fazer a tradução abrir o arquivo template.pot no POEDIT e criar uma nova tradução com o nome da linguagem desejada (pt_BR, en, en-US por exemplo) e traduzir as palavras

5) Caso seja apenas uma atualização de alguma tradução existente abrir o arquivo .po da tradução no POEDIT ir no menu Catálogo->Atualizar do arquivo POT, escolher o arquivo template.pot e traduzir as novas palavras

6) Rodar o comando grunt nggettext_compile


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