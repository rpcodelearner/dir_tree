# dir_tree
This CLI program displays a tree of directories / folders on standard output (stdout).
## Program Arguments
Current version of the program accepts exactly one string argument, which it interprets as a pathname to a directory (a folder).
## Error Messages and Exit Status Codes
The program returns 0 after successful execution.

If the provided string does not match an existing directory, the program prints an error message and exits with exit status code 1. 

If the number of arguments is different from 1, a "usage" message is printed and the program terminates with exit status code 2.

Runtime errors, for example not having permission to access a filesystem item, are not handled in the current version and result in a runtime-exception.
