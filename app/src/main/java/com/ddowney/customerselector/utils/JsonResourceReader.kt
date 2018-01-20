package com.ddowney.customerselector.utils

import android.content.res.Resources
import android.util.Log
import com.google.gson.Gson
import java.io.*

/**
 * Created by Dan on 20/01/2018.
 *
 * Solution based on:
 * https://stackoverflow.com/questions/6349759/using-json-file-in-android-app-resources
 *
 * Read from a resources file and create a [JsonResourceReader] object that
 * will allow the creation of objects from this source.
 *
 * @param resources An application {@link Resources} object
 * @param id The id of the resource to be loaded
 */
class JsonResourceReader (resources : Resources, val id : Int, val gson : Gson) {
    private val LOG_TAG = "JsonResourceReader"
    private val jsonObjects = mutableListOf<String>()

    init {
        val resourceReader : InputStream = resources.openRawResource(id)

        try {
            val reader = BufferedReader(InputStreamReader(resourceReader, "UTF-8"))
            var line : String? = reader.readLine()
            while (line != null) {
                val writer : Writer = StringWriter()
                writer.write(line)
                line = reader.readLine()
                jsonObjects.add(writer.toString())
            }
        } catch (e : IOException) {
            Log.e(LOG_TAG, "Problem using JsonResourceReader", e)
        } finally {
            try {
                resourceReader.close()
            } catch (e : IOException) {
                Log.e(LOG_TAG, "Problem closing the resource reader", e)
            }
        }

    }

    /**
     * Builds a list of objects from the specified JSON resource
     *
     * @return A list of objects of type <T>
     */
    fun <T> constructUsingGson(type : Class<T>): List<T> {
        val objects = mutableListOf<T>()
        jsonObjects.forEach { jsonString ->
            objects.add(gson.fromJson(jsonString, type))
        }
        return objects
    }
}