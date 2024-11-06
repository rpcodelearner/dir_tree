# dir_tree
This CLI program displays a tree of directories / folders on standard output (stdout).
## Program Arguments
Synopsis:

` [-f] ROOT_DIR_PATH_NAME`
<br>`-f also include files in the results`
<br>Redundant applications of `-f` are ignored. There must be *exactly one* complete pathname, which the app interprets as the path+name of the root directory to examine. 
## Error Messages and Exit Status Codes
The program returns 0 after successful execution.

If the provided string does not match an existing directory, the program prints an error message and exits with exit status code 1. 

With malformed arguments, a "usage" message is printed and the program terminates with exit status code 2.

Runtime errors, for example not having permission to access a filesystem item, are not handled in the current version and result in a runtime-exception.
