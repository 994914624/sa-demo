source ~/.profile
export ANDROID_HOME=/Users/yang/Library/Android/sdk
export JADX_HOME=/Users/yang/Desktop/project/jadx/build/jadx/bin
export DEX2=/Users/yang/Desktop/project/dex2jar-2.0
export MAVEN=/Users/yang/apache-maven-3.5.0/bin
export AAPT=/Users/yang/Library/Android/sdk/build-tools/25.0.2
PATH=$ANDROID_HOME/platform-tools:$ANDROID_HOME/tools:$JADX_HOME:$DEX2:$AAPT:$MAVEN:$PATH
export PATH
export ANDROID_NDK_HOME=/usr/local/opt/android-ndk

[[ -s "$HOME/.rvm/scripts/rvm" ]] && source "$HOME/.rvm/scripts/rvm" # Load RVM into a shell session *as a function*


# Setting PATH for Python 3.5
# The original version is saved in .bash_profile.pysave
PATH="/Library/Frameworks/Python.framework/Versions/3.5/bin:${PATH}"
export PATH

#THIS MUST BE AT THE END OF THE FILE FOR SDKMAN TO WORK!!!
export SDKMAN_DIR="/Users/yang/.sdkman"
[[ -s "/Users/yang/.sdkman/bin/sdkman-init.sh" ]] && source "/Users/yang/.sdkman/bin/sdkman-init.sh"
