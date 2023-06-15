import dev.kord.core.Kord
import dev.kord.core.event.message.MessageCreateEvent
import dev.kord.core.on
import dev.kord.gateway.Intent
import dev.kord.gateway.PrivilegedIntent
import io.github.cdimascio.dotenv.dotenv
import io.ktor.utils.io.core.*
import java.io.BufferedWriter
import java.io.File


val dotenv = dotenv()
suspend fun main() {
    val file = File("sugestoes.txt")
    val kord = Kord(dotenv["TOKEN"])
    val suggestions: MutableList<String> = mutableListOf()
    kord.on<MessageCreateEvent> {
        if (message.author?.isBot != false) return@on
        when (message.content) {
            "!ping" -> message.channel.createMessage("pong!")
            "!pin" -> {
                message.referencedMessage!!.pin("pinned by Robertinho.")
                message.channel.createMessage("Mensagem fixada!")
            }

            "!help" -> message.channel.createMessage(
                "Olá! Aqui estão algumas coisas que posso fazer: \n" + "!pin: Irei fixar a mensagem referenciada no canal de texto citada. \n" + "!help: Lista de funções disponíveis. \n" + "!addSuggestion {sugestão}: Caso queira adicionar uma sugestão de futuras implementações de funcionalidades do bot :)."
            )


            "!getSuggestions" -> {
                if (message.author!!.username == "josev.moraes") message.channel.createMessage("Aqui está: \n" + suggestions.let { return@let "$it \n" })
            }

            else -> return@on
        }

    }

    kord.on<MessageCreateEvent> {
        if (message.content == "!teste") message.channel.createMessage(message.author!!.username)
        if (message.content.startsWith("!addSuggestion")) {
            message.channel.createMessage("Obrigado pela sugestão! :)")
            suggestions.add(index = suggestions.lastIndex + 1, element = message.content.removePrefix("!addSuggestion"))
            file.appendText(message.content.removePrefix("!addSuggestion") + "\n")
        }
    }

    kord.login {
        @OptIn(PrivilegedIntent::class)
        intents += Intent.MessageContent
    }
}