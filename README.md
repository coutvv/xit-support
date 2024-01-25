# xit-support 

IntelliJ IDEA plugin for supporting [\[x\]it!](https://xit.jotaen.net) 
file format

## Getting started 

For developing you should install 
[Grammar-Kit plugin](https://plugins.jetbrains.com/plugin/6606-grammar-kit)
in your IntelliJ IDEA.

Open `build.gradle.kts` file as project in IntelliJ IDEA

Build project using gradle task `build`

For running instance of Idea with plugin run Gradle 
task: `intellij -> runIde`

## Troubleshooting

For better performance you could turn off generating
lexer and parser in gradle tasks  `withType<KotlinCompile>` 
by commenting `dependsOn(generateLexer, generateParser)` 
in the `build.gradle.kts`. And then you will have to generate
Lexer and Parser manually:
- Right click on `Xit.bnf` file -> Generate Parser Code
- Right click on `Xit.flex` file -> Run JFlex Generator
