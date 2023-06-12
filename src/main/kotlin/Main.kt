import dev.kord.core.Kord
import dev.kord.core.event.message.MessageCreateEvent
import dev.kord.core.on
import dev.kord.gateway.Intent
import dev.kord.gateway.PrivilegedIntent
import io.github.cdimascio.dotenv.dotenv

val dotenv = dotenv()
suspend fun main() {
    val kord = Kord(dotenv["TOKEN"])

    kord.on<MessageCreateEvent> {
        if (message.author?.isBot != false) return@on
        if (message.content != "!ping") return@on
        
        message.channel.createMessage("pong!")
    }

    kord.login {
        @OptIn(PrivilegedIntent::class)
        intents += Intent.MessageContent
    }
}