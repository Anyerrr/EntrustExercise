SOURCE_DIR = src
JAR_NAME = EntrustExercise.jar
BUILD_DIR = out/production/EntrustExercise
MAIN_CLASS = Main
JAR_FILE = out/$(JAR_NAME)

# Compile Java source files
compile:
	javac -d $(BUILD_DIR) $(SOURCE_DIR)/*.java

# Run the compiled application
run: compile
	java -cp $(BUILD_DIR) $(MAIN_CLASS)

# Create the JAR file
jar:
	jar cfe $(JAR_FILE) $(MAIN_CLASS) -C $(BUILD_DIR) .

# Execute the application from the JAR file
runJar: jar
	java -jar $(JAR_FILE)


ifeq ($(OS),Windows_NT)
    RM = del
else
    RM = rm -f
endif

# Clean generated files
clean:
	$(RM) out