import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.externship.kotlinexternshipteamproject.presentation.add_edit_expanse.componants.Chip

@OptIn(
    ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class,
    ExperimentalComposeUiApi::class
)
@Composable
fun ChipInputField(
    tags: List<String>,
    onSpacePressed: (List<String>) -> Unit
) {
    val (text, setText) = remember { mutableStateOf("") }
    val (chips, setChips) = remember { mutableStateOf(listOf<String>()) }
    //write the code to show incoming tags
    val newTextValue = remember {
        mutableStateOf("")
    }
    val keyboardController = LocalSoftwareKeyboardController.current

    Column {
        OutlinedTextField(
            value = text,
            onValueChange = { newText ->
                newTextValue.value = newText
                if (newText.endsWith(" ")) {
                    newTextValue.value = ""
                    setChips(chips + newText.dropLast(1))
                    setText("")
                } else {
                    setText(newText)
                }

            },
            leadingIcon = {
                FlowRow(
                    modifier = Modifier
                        .padding(8.dp)
                        .widthIn(max = 200.dp)
                ) {
                    onSpacePressed(chips)
                    chips.forEach { chip ->
                        Chip(modifier = Modifier.padding(2.dp), label = chip, onChipClick = {

                        })
                    }
                }
            },
            label = { Text("Tags") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {
                    //write the code to set chip when done pressed
                    println("newTextValue: $newTextValue")
                    if (newTextValue.value.isNotEmpty()) {
                        setChips(chips + newTextValue.value.dropLast(1))
                        setText("")
                        newTextValue.value = ""
                    }
                    keyboardController?.hide()
                }
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .onKeyEvent { event ->
                    if (event.type == KeyEventType.KeyUp && event.key == Key.Backspace && text.isEmpty()) {
                        if (chips.isNotEmpty()) {
                            setChips(chips.dropLast(1))
                            true
                        } else {
                            false
                        }
                    } else {
                        false
                    }
                }
        )
    }
}
