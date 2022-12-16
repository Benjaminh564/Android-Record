package com.finalteam6.HighResAudioRecorder

import org.junit.Assert
import org.junit.Test
import org.junit.Assert.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class TimerTest
{
    @Test
    fun TimerRepresentation45365456545()
    {
        val correct = "12601:30:56";
        assertEquals(correct, fragment_record.timerRepresentation(45365456545));
    }
    @Test
    fun TimerRepresentation3453453245()
    {
        val correct = "959:17:33";
        assertEquals(correct, fragment_record.timerRepresentation(3453453245));
    }
    @Test
    fun TimerRepresentation345345432543()
    {
        val correct = "95929:17:12";
        assertEquals(correct, fragment_record.timerRepresentation(345345432543));
    }
    @Test
    fun TimerRepresentation435345345345()
    {
        val correct = "120929:15:45";
        assertEquals(correct, fragment_record.timerRepresentation(435345345345));
    }
    @Test
    fun TimerRepresentation435345()
    {
        val correct = "00:07:15";
        assertEquals(correct, fragment_record.timerRepresentation(435345));
    }
    @Test
    fun TimerRepresentation4354353454()
    {
        val correct = "1209:32:33";
        assertEquals(correct, fragment_record.timerRepresentation(4354353454));
    }
    @Test
    fun TimerRepresentation3455343453452()
    {
        val correct = "959817:37:33";
        assertEquals(correct, fragment_record.timerRepresentation(3455343453452));
    }
    @Test
    fun TimerRepresentation356364634()
    {
        val correct = "98:59:24";
        assertEquals(correct, fragment_record.timerRepresentation(356364634));
    }
    @Test
    fun TimerRepresentation3464326436()
    {
        val correct = "962:18:46";
        assertEquals(correct, fragment_record.timerRepresentation(3464326436));
    }
    @Test
    fun TimerRepresentation34643643643()
    {
        val correct = "9623:14:03";
        assertEquals(correct, fragment_record.timerRepresentation(34643643643));
    }
    @Test
    fun TimerRepresentation34543543543()
    {
        val correct = "9595:25:43";
        assertEquals(correct, fragment_record.timerRepresentation(34543543543));
    }
    @Test
    fun TimerRepresentation346346436543()
    {
        val correct = "96207:20:36";
        assertEquals(correct, fragment_record.timerRepresentation(346346436543));
    }
    @Test
    fun TimerRepresentation34543543534()
    {
        val correct = "9595:25:43";
        assertEquals(correct, fragment_record.timerRepresentation(34543543534));
    }
    @Test
    fun TimerRepresentation8958594()
    {
        val correct = "02:29:18";
        assertEquals(correct, fragment_record.timerRepresentation(8958594));
    }
    @Test
    fun TimerRepresentation569053()
    {
        val correct = "00:09:29";
        assertEquals(correct, fragment_record.timerRepresentation(569053));
    }
}