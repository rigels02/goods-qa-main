Questions and Answers JavaFX XML  application
=============================================
Uses:
- JavaFX
- QA-Module for Questions/Answers (Q/A) data processing and xml based storage.

Features:
- Export Q/A data to file by using JAXB parser.
- Export Q/A data to file by using Simple-Xml parser.

~~~~
 <dependency>
            <groupId>org.simpleframework</groupId>
            <artifactId>simple-xml</artifactId>
            <version>2.7.1</version>
 </dependency>
~~~~

- Add/edit/delete Q/A records.
  Answer data can be edited and saved in both format:  Html or Markdown text format.  

QA- Module
==========
The application works with knowledge based data having question-answer (Q/A) structure.
It can be any kind of information data, for example, interview questions and answers.
So, the data model used is the Q/A list.
The QA-Module (project qa-module) is responsible for data processing in 
knowledge base KNBase and have interface with xml parser based storage.
