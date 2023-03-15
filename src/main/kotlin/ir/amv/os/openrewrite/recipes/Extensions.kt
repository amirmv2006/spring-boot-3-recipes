package ir.amv.os.openrewrite.recipes

import org.openrewrite.ExecutionContext
import org.openrewrite.Recipe
import org.openrewrite.TreeVisitor
import org.openrewrite.internal.StringUtils
import org.openrewrite.maven.MavenVisitor
import org.openrewrite.maven.tree.MavenResolutionResult
import org.openrewrite.xml.tree.Xml
import org.openrewrite.xml.tree.Xml.Tag
import kotlin.jvm.optionals.getOrElse

open class ImprovedMavenVisitor : MavenVisitor<ExecutionContext>() {
    public override fun getResolutionResult(): MavenResolutionResult {
        return super.getResolutionResult()
    }

    public override fun doAfterVisit(recipe: Recipe) {
        super.doAfterVisit(recipe)
    }

    public override fun doAfterVisit(visitor: TreeVisitor<Xml, ExecutionContext>) {
        super.doAfterVisit(visitor)
    }
}

fun visitMavenTag(visitFn: context(ImprovedMavenVisitor)(Tag, ExecutionContext) -> Tag?): MavenVisitor<ExecutionContext> {
    val visitor = object : ImprovedMavenVisitor() {
        override fun visitTag(tag: Tag, p: ExecutionContext): Xml {
            val visitTag = super.visitTag(tag, p) as Tag
            return visitFn(this, visitTag, p) ?: visitTag
        }
    }
    return visitor
}

context(ImprovedMavenVisitor)
@OptIn(ExperimentalStdlibApi::class)
fun Tag.hasMavenSpec(groupId: String, artifactId: String) =
    StringUtils.matchesGlob(
        getChildValue("groupId").getOrElse { resolutionResult.pom.groupId },
        "com.booking.community.springbook"
    ) && StringUtils.matchesGlob(
        getChildValue("artifactId").getOrElse { resolutionResult.pom.artifactId },
        "testable-infra-patcher"
    )
