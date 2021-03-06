Java CLIR Parser
================
[![Build Status](https://travis-ci.org/cavi-art/clir-parser-java.svg?branch=master)](https://travis-ci.org/cavi-art/clir-parser-java)
[![Download with JitPack](https://jitpack.io/v/cavi-art/clir-parser-java.svg)](https://jitpack.io/#cavi-art/clir-parser-java)

This is a CAVI-ART IR Parser written in Java. After parsing, the full 
Abstract Syntax Tree is generated so that further analysis can be performed
on the structure.


Description
===========

In order to parse an IR file from your project, you just need to create a 
`new IRFileParser(InputStream in)` from your desired InputStream. After 
that, `irFileParser.parseAllToplevel()` returns all the toplevel forms. 
Further information can be seen in the interfaces and implementing classes.


Examples
========

Examples can be found on the vcgen repository at [cavi-art/clir-vcgen][vcgen].


Using on your own project
=========================

This project is meant to be used as a library.

- You can include it as a git submodule in your own sources
- You can include the built jar in your lib/ folder (get the latest one from the [releases tab][rel]).
- **Use maven!** (That's the recommended approach)

Using maven
-----------

You can get this project from JitPack using maven (or sbt, gradle or leiningen).
For more information, see: https://jitpack.io/#cavi-art/clir-parser-java

In short, just add the project as a dependency, and include JitPack as a repository in your `pom.xml`.


Contributing
============

This project encourages the [GitHub Flow][flow] for external contributions. 
Please send any improvements you may find via GitHub a Pull Request.

By sending a Pull Request you agree to publish your own code under the same 
license as the one stated in the repository.
  

Acknowledgements
================

This work is partially supported by
the Spanish MINECO project CAVI-ART (TIN2013-44742-C4-3-R),
Madrid regional project N-GREENS Software-CM (S2013/ICE-2731) and
UCM grant GR3/14-910502.

CAVI-ART stands for Computer Assisted ValIdation by Analysis, 
tRansformation and Testing.


  [flow]: https://guides.github.com/introduction/flow/
  [rel]: https://github.com/cavi-art/clir-parser-java/releases
  [vcgen]: https://github.com/cavi-art/clir-vcgen/tree/master/test
