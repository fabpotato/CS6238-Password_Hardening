JC = javac
JVM = java
BINDIR = /edu/gatech/scs/pwd/hardening
.SUFFIXES: .java .class
.java.class:
	$(JC) -d . $*.java

CLASSES = \
	encryptAndDecrypt.java \
        initialize.java \
        LoginAttempt.java \
	hardeningMainClass.java 

MAIN = Main

default: classes

classes: $(CLASSES:.java=.class)

run: $(MAIN).class
	$(JVM) $(BINDIR)/$(MAIN)
clean:
	$(RM) *.class
