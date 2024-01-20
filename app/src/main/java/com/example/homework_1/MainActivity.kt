package com.example.homework_1

import SampleData
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.homework_1.ui.theme.Homework_1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Homework_1Theme {
                Conversation(messages = SampleData.conversationSample)
                Surface(modifier = Modifier.fillMaxSize()) {
                    MessageCard(Message("Android", "Jetpack Compose"))
                }
            }
        }
    }
}

data class Message(val author: String, val body: String)

@Composable
fun MessageCard(msg: Message) {
    // Add padding around the message
    Row (modifier = Modifier.padding(all = 8.dp)) {
        Image(
            painter = painterResource(R.drawable.skull),
            contentDescription = "skull emoji",
            modifier = Modifier
                // Set image size to 40 dp
                .size(40.dp)
                // Clip image to circle shape
                .clip(CircleShape)
                // Add a colored border around image
                .border(1.5.dp, MaterialTheme.colorScheme.primary, CircleShape)
        )

        // Add a horizontal space between the image and the column
        Spacer(modifier = Modifier.width(8.dp))

        // We keep track if the message is expanded or not in this
        // variable
        var isExpanded by remember {
            mutableStateOf(false)
        }

        // surfaceColor will be updated gradually from one color to the other
        val surfaceColor by animateColorAsState(
            if (isExpanded) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
            label = ""
        )

        // We toggle the isExpanded variable when we click on this Column

        Column (modifier = Modifier.clickable { isExpanded = !isExpanded }) {

            Text(
                text = msg.author,
                // Change text color
                color = MaterialTheme.colorScheme.secondary,
                // Change the typography style
                style = MaterialTheme.typography.titleSmall
            )

            // Add a vertical space between the author and the message
            Spacer(modifier = Modifier.height(4.dp))

            Surface(
                shape = MaterialTheme.shapes.small,
                shadowElevation = 1.dp,
                // surfaceColor color will be changing gradually from primary to surface
                color = surfaceColor,
                // animateContentSize will change the Surface size gradually
                modifier = Modifier.animateContentSize().padding(1.dp)
            ) {
                Text(
                    text = msg.body,
                    // Add padding around text
                    modifier = Modifier.padding(all = 4.dp),
                    // If the message is expanded, we display all its content
                    // otherwise we only display the first line
                    maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                    // Change the typography style
                    style = MaterialTheme.typography.bodyMedium
                )
            }

        }
    }
}

@Composable
fun Conversation(messages: List<Message>) {
    LazyColumn {
        items(messages) { message -> 
            MessageCard(msg = message)
        }
    }
}

@Preview
@Composable
fun PreviewConversation() {
    Homework_1Theme {
        Conversation(SampleData.conversationSample)
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "Light Mode"
    )
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@Composable
fun PreviewMessageCard() {
    Homework_1Theme {
        Surface {
            MessageCard(
                msg = Message("Mr. Skull Emoji", "Take a look at Jetpack Compose, it's great!")
            )
        }
    }
}