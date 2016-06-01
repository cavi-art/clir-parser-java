Java CLIR Parser
================

This is a CAVI-ART IR Parser written in Java. After parsing, the full 
Abstract Syntax Tree is generated so that further analysis can be performed
on the structure.


Description
===========

In order to parse an IR file from your project, you just need to create a 
`new IRFileParser(InputStream in)` from your desired InputStream. After 
that, `irFileParser.parseAllToplevel()` returns all the toplevel forms. 
Further information can be seen in the interfaces and implementing classes.


Using on your own project
=========================

This project is meant to be used as a library. It can either be included as
a git submodule in your own sources or just include the built jar in your 
lib folder (get the latest built jar from the [releases][rel]).


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