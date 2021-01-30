package com.matbadev.dabirva.util

import androidx.databinding.Observable
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.concurrent.atomic.AtomicReference

class NonNullObservableFieldTest {

    @Test
    fun `GIVEN non-null initial value WHEN creating instance EXPECT success`() {
        val initialValue = 42
        NonNullObservableField(initialValue)
    }

    @Test
    fun `GIVEN non-null initial value WHEN creating instance and accessing value EXPECT value`() {
        val value = "text"
        val observableField = NonNullObservableField(value)
        assertEquals(value, observableField.value)
    }

    @Test
    fun `GIVEN null initial value WHEN creating instance EXPECT exception`() {
        val nullRef = AtomicReference<Any>(null)
        assertThrows<NullPointerException> {
            NonNullObservableField(nullRef.get())
        }
    }

    @Test
    fun `GIVEN non-null new value WHEN setting value EXPECT value update`() {
        val observableField = NonNullObservableField("text")
        var propertyChangedValue: String? = null
        observableField.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            @Suppress("UNCHECKED_CAST")
            override fun onPropertyChanged(sender: Observable, propertyId: Int) {
                val field = sender as NonNullObservableField<String>
                //propertyChangedValue = field.value
            }
        })
        val newValue = "updatedText"
        observableField.value = newValue
        assertEquals(newValue, observableField.value)
        assertEquals(newValue, propertyChangedValue)
    }

    @Test
    fun `GIVEN null new value WHEN setting value EXPECT exception`() {
        val nullStringRef = AtomicReference<String>(null)
        val observableField = NonNullObservableField("")
        assertThrows<NullPointerException> {
            observableField.value = nullStringRef.get()
        }
    }

}
