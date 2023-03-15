package ir.amv.os.openrewrite.recipes

import org.junit.jupiter.api.Test
import org.openrewrite.maven.Assertions
import org.openrewrite.test.RewriteTest
import java.util.function.Consumer


class MigrateOpenApiGeneratorTest : RewriteTest {

    @Test
    fun `should change openapi configurations when plugin with config`() {
        rewriteRun(
            Consumer {
                it.recipe(MigrateOpenApiGenerator())
            },
            Assertions.pomXml(
                """
                  <project>
                      <groupId>org.example</groupId>
                      <artifactId>foo</artifactId>
                      <version>1.0</version>
                      <build>
                          <plugins>
                              <plugin>
                                <groupId>org.openapitools</groupId>
                                <artifactId>openapi-generator-maven-plugin</artifactId>
                                <version>6.3.0</version>
                                <configuration>
                                    <!-- specify the swagger yaml -->
                                    <inputSpec>${'$'}{project.basedir}/src/main/resources/spec/api.yaml</inputSpec>
                                    <!-- target to generate java client code -->
                                    <generatorName>spring</generatorName>
                                    <!-- hint: if you want to generate java server code, e.g. based on Spring Boot,
                                    you can use the following target: <generatorName>spring</generatorName> -->
                                    <!-- pass any necessary config options -->
                                    <configOptions>
                                        <interfaceOnly>true</interfaceOnly>
                                        <skipDefaultInterface>true</skipDefaultInterface>
                                        <useTags>true</useTags>
                                        <documentationProvider>springfox</documentationProvider>
                                        <sourceFolder>src/gen/java/main</sourceFolder>
                                        <basePackage>${'$'}{generated.base.package}</basePackage>
                                        <configPackage>${'$'}{generated.base.package}.config</configPackage>
                                        <modelPackage>${'$'}{generated.base.package}.model</modelPackage>
                                        <apiPackage>${'$'}{generated.base.package}.api</apiPackage>
                                    </configOptions>
                                </configuration>
                                <executions>
                                    <execution>
                                        <id>spring-server</id>
                                        <goals>
                                            <goal>generate</goal>
                                        </goals>
                                    </execution>
                                </executions>
                            </plugin>
                          </plugins>
                      </build>
                  </project>
                  """.trimIndent().replace(" ", ""),
                """
                  <project>
                      <groupId>org.example</groupId>
                      <artifactId>foo</artifactId>
                      <version>1.0</version>
                      <build>
                          <plugins>
                              <plugin>
                                <groupId>org.openapitools</groupId>
                                <artifactId>openapi-generator-maven-plugin</artifactId>
                                <version>6.3.0</version>
                                <configuration>
                                    <!-- specify the swagger yaml -->
                                    <inputSpec>${'$'}{project.basedir}/src/main/resources/spec/api.yaml</inputSpec>
                                    <!-- target to generate java client code -->
                                    <generatorName>spring</generatorName>
                                    <!-- hint: if you want to generate java server code, e.g. based on Spring Boot,
                                    you can use the following target: <generatorName>spring</generatorName> -->
                                    <!-- pass any necessary config options -->
                                    <configOptions>
                                        <interfaceOnly>true</interfaceOnly>
                                        <skipDefaultInterface>true</skipDefaultInterface>
                                        <useTags>true</useTags>
                                        <documentationProvider>springdoc</documentationProvider>
                                        <sourceFolder>src/gen/java/main</sourceFolder>
                                        <basePackage>${'$'}{generated.base.package}</basePackage>
                                        <configPackage>${'$'}{generated.base.package}.config</configPackage>
                                        <modelPackage>${'$'}{generated.base.package}.model</modelPackage>
                                        <apiPackage>${'$'}{generated.base.package}.api</apiPackage>
                                        <useSpringBoot3>true</useSpringBoot3>
                                    </configOptions>
                                </configuration>
                                <executions>
                                    <execution>
                                        <id>spring-server</id>
                                        <goals>
                                            <goal>generate</goal>
                                        </goals>
                                    </execution>
                                </executions>
                            </plugin>
                          </plugins>
                      </build>
                  </project>
                  """
                    .trimIndent().replace(" ", "")
            )
        )
    }
    @Test
    fun `should change openapi configurations to sb3 when one execution`() {
        rewriteRun(
            Consumer {
                it.recipe(MigrateOpenApiGenerator())
            },
            Assertions.pomXml(
                """
                  <project>
                      <groupId>org.example</groupId>
                      <artifactId>foo</artifactId>
                      <version>1.0</version>
                      
                      <build>
                          <plugins>
                              <plugin>
                                <groupId>org.openapitools</groupId>
                                <artifactId>openapi-generator-maven-plugin</artifactId>
                                <!-- RELEASE_VERSION -->
                                <version>6.3.0</version>
                                <!-- /RELEASE_VERSION -->
                                <executions>
                                    <execution>
                                        <id>spring-server</id>
                                        <goals>
                                            <goal>generate</goal>
                                        </goals>
                                        <configuration>
                                            <!-- specify the swagger yaml -->
                                            <inputSpec>${'$'}{project.basedir}/src/main/resources/spec/api.yaml</inputSpec>
                                            <!-- target to generate java client code -->
                                            <generatorName>spring</generatorName>
                                            <!-- hint: if you want to generate java server code, e.g. based on Spring Boot,
                                            you can use the following target: <generatorName>spring</generatorName> -->
                                            <!-- pass any necessary config options -->
                                            <configOptions>
                                                <interfaceOnly>true</interfaceOnly>
                                                <skipDefaultInterface>true</skipDefaultInterface>
                                                <useTags>true</useTags>
                                                <documentationProvider>springfox</documentationProvider>
                                                <sourceFolder>src/gen/java/main</sourceFolder>
                                                <basePackage>${'$'}{generated.base.package}</basePackage>
                                                <configPackage>${'$'}{generated.base.package}.config</configPackage>
                                                <modelPackage>${'$'}{generated.base.package}.model</modelPackage>
                                                <apiPackage>${'$'}{generated.base.package}.api</apiPackage>
                                            </configOptions>
                                        </configuration>
                                    </execution>
                                </executions>
                            </plugin>
                          </plugins>
                      </build>
                  </project>
                  """.trimIndent(),
                """
                  <project>
                      <groupId>org.example</groupId>
                      <artifactId>foo</artifactId>
                      <version>1.0</version>
                      
                      <build>
                          <plugins>
                              <plugin>
                                <groupId>org.openapitools</groupId>
                                <artifactId>openapi-generator-maven-plugin</artifactId>
                                <!-- RELEASE_VERSION -->
                                <version>6.3.0</version>
                                <!-- /RELEASE_VERSION -->
                                <executions>
                                    <execution>
                                        <id>spring-server</id>
                                        <goals>
                                            <goal>generate</goal>
                                        </goals>
                                        <configuration>
                                            <!-- specify the swagger yaml -->
                                            <inputSpec>${'$'}{project.basedir}/src/main/resources/spec/api.yaml</inputSpec>
                                            <!-- target to generate java client code -->
                                            <generatorName>spring</generatorName>
                                            <!-- hint: if you want to generate java server code, e.g. based on Spring Boot,
                                            you can use the following target: <generatorName>spring</generatorName> -->
                                            <!-- pass any necessary config options -->
                                            <configOptions>
                                                <interfaceOnly>true</interfaceOnly>
                                                <skipDefaultInterface>true</skipDefaultInterface>
                                                <useTags>true</useTags>
                                                <documentationProvider>springdoc</documentationProvider>
                                                <sourceFolder>src/gen/java/main</sourceFolder>
                                                <basePackage>${'$'}{generated.base.package}</basePackage>
                                                <configPackage>${'$'}{generated.base.package}.config</configPackage>
                                                <modelPackage>${'$'}{generated.base.package}.model</modelPackage>
                                                <apiPackage>${'$'}{generated.base.package}.api</apiPackage>
                                                <useSpringBoot3>true</useSpringBoot3>
                                            </configOptions>
                                        </configuration>
                                    </execution>
                                </executions>
                            </plugin>
                          </plugins>
                      </build>
                  </project>
                  """
                    .trimIndent()
            )
        )
    }

    @Test
    fun `should change openapi configurations to sb3 when multiple executions`() {
        rewriteRun(
            Consumer {
                it.recipe(MigrateOpenApiGenerator())
            },
            Assertions.pomXml(
                """
                  <project>
                      <groupId>org.example</groupId>
                      <artifactId>foo</artifactId>
                      <version>1.0</version>
                      
                      <build>
                          <plugins>
                              <plugin>
                                <groupId>org.openapitools</groupId>
                                <artifactId>openapi-generator-maven-plugin</artifactId>
                                <!-- RELEASE_VERSION -->
                                <version>6.3.0</version>
                                <!-- /RELEASE_VERSION -->
                                <executions>
                                    <execution>
                                        <id>spring-server</id>
                                        <goals>
                                            <goal>generate</goal>
                                        </goals>
                                        <configuration>
                                            <!-- specify the swagger yaml -->
                                            <inputSpec>${'$'}{project.basedir}/src/main/resources/spec/api.yaml</inputSpec>
                                            <!-- target to generate java client code -->
                                            <generatorName>spring</generatorName>
                                            <!-- hint: if you want to generate java server code, e.g. based on Spring Boot,
                                            you can use the following target: <generatorName>spring</generatorName> -->
                                            <!-- pass any necessary config options -->
                                            <configOptions>
                                                <interfaceOnly>true</interfaceOnly>
                                                <skipDefaultInterface>true</skipDefaultInterface>
                                                <useTags>true</useTags>
                                                <documentationProvider>springfox</documentationProvider>
                                                <sourceFolder>src/gen/java/main</sourceFolder>
                                                <basePackage>${'$'}{generated.base.package}</basePackage>
                                                <configPackage>${'$'}{generated.base.package}.config</configPackage>
                                                <modelPackage>${'$'}{generated.base.package}.model</modelPackage>
                                                <apiPackage>${'$'}{generated.base.package}.api</apiPackage>
                                            </configOptions>
                                        </configuration>
                                    </execution>
                                </executions>
                            </plugin>
                          </plugins>
                      </build>
                  </project>
                  """.trimIndent(),
                """
                  <project>
                      <groupId>org.example</groupId>
                      <artifactId>foo</artifactId>
                      <version>1.0</version>
                      
                      <build>
                          <plugins>
                              <plugin>
                                <groupId>org.openapitools</groupId>
                                <artifactId>openapi-generator-maven-plugin</artifactId>
                                <!-- RELEASE_VERSION -->
                                <version>6.3.0</version>
                                <!-- /RELEASE_VERSION -->
                                <executions>
                                    <execution>
                                        <id>spring-server</id>
                                        <goals>
                                            <goal>generate</goal>
                                        </goals>
                                        <configuration>
                                            <!-- specify the swagger yaml -->
                                            <inputSpec>${'$'}{project.basedir}/src/main/resources/spec/api.yaml</inputSpec>
                                            <!-- target to generate java client code -->
                                            <generatorName>spring</generatorName>
                                            <!-- hint: if you want to generate java server code, e.g. based on Spring Boot,
                                            you can use the following target: <generatorName>spring</generatorName> -->
                                            <!-- pass any necessary config options -->
                                            <configOptions>
                                                <interfaceOnly>true</interfaceOnly>
                                                <skipDefaultInterface>true</skipDefaultInterface>
                                                <useTags>true</useTags>
                                                <documentationProvider>springdoc</documentationProvider>
                                                <sourceFolder>src/gen/java/main</sourceFolder>
                                                <basePackage>${'$'}{generated.base.package}</basePackage>
                                                <configPackage>${'$'}{generated.base.package}.config</configPackage>
                                                <modelPackage>${'$'}{generated.base.package}.model</modelPackage>
                                                <apiPackage>${'$'}{generated.base.package}.api</apiPackage>
                                                <useSpringBoot3>true</useSpringBoot3>
                                            </configOptions>
                                        </configuration>
                                    </execution>
                                </executions>
                            </plugin>
                          </plugins>
                      </build>
                  </project>
                  """
                    .trimIndent()
            )
        )
    }
}