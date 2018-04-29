/set feedback
/set mode dopamine normal -command
/set feedback dopamine
/set prompt dopamine "\033[1;33mprompt %s> \033[0m" "\033[1;33m          ..."
/set format dopamine result "{name} ==> \033[1;32m{value}{post}" added,modified,replaced-primary-ok
/set editor -retain /usr/bin/vim
/set mode dopamine -retain
