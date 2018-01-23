Questions and Answers processing example
========================================
References:
https://www.mkyong.com/tutorials/java-xml-tutorials/?utm_source=mkyong&utm_medium=author&utm_campaign=related-post&utm_content=9
https://stackoverflow.com/questions/31656836/markdown-support-in-android-textview
http://brainlow.blogspot.com/2012/10/javafx-markdown-viewer.html
http://simple.sourceforge.net/

Technologies
------------
JAXB and SimpleXML (for Android)

The StorageFactories is used to get Factory 
who implements IStorageFactory interface  responsible for xml  
serializing/deserializing using xml parser( Jaxb, Simple Xml, etc) or even other kind
of storage. It could be ,for example, DB access implementation. 

Before use of StorageFactories the factories map must be initialized by the 
configFactories() method.

~~~~
  Example:
      usedStorages= new HashMap();
      usedStorages.put(StorageType.Default, new JaxbFactory());
      usedStorages.put(StorageType.JaxbStorage, new JaxbFactory());
      ...
      StorageFactories.configFactories(usedStorages);
~~~~

Xml and data models
-------------------
Between KNBase/QA data model classes in qa-module and XML serialization/deserialization (s/d) 
there are xml specific data model classes containing annotations to control data s/d process. 
Therefore, \<xml\>Factory is responsible to copy data from/to KNBase/QA data model and xml 
specific data model classes. 
See, for example, JaxbFactory and package org.rb.storage.jaxb for more details.    