package com.slava_110.androidkotlinpractice.tasks.serverdata.repository

import androidx.room.*

@Database(
    entities = [Answer::class],
    version = 1,
    exportSchema = false
)
abstract class ServerDataDatabase: RoomDatabase() {

    abstract fun getServerDataDao(): ServerDataDao
}

@Entity
data class Answer(
    @PrimaryKey(autoGenerate = true)
    val uid: Int,
    @ColumnInfo(name = "yes_or_no")
    val yesOrNo: String
)


@Dao
interface ServerDataDao {
    @Query("SELECT * FROM answer")
    fun getAll(): List<Answer>

    @Insert
    fun insert(answer: Answer)
}