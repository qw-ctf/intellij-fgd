import com.intellij.psi.stubs.StubIndexKey
import org.intellij.sdk.language.psi.FgdClassName

object FgdIndexKeys {
	val DECLARATIONS: StubIndexKey<String, FgdClassName> =
		StubIndexKey.createIndexKey(FgdIndexKeys::class.java.packageName + "::classNames")
}
