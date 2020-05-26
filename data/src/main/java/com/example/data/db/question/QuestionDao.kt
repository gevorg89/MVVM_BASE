package com.example.data.db.question

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.data.db.BaseDao
import io.reactivex.Flowable


@Dao
interface QuestionDao : BaseDao<QuestionData.Question> {
    @Query("SELECT * FROM question WHERE question_id = :id")
    override fun select(id: Long): Flowable<QuestionData.Question>

    @Query("SELECT * FROM question ORDER BY question_id")
    override fun selectAllPaged(): DataSource.Factory<Int, QuestionData.Question>

    @Query("DELETE FROM question")
    override fun truncate()

    @Transaction
    fun replace(questions: List<QuestionData.Question>) {
        val firstId: Long = questions.firstOrNull()?.question_id ?: run {
            0L
        }

        val lastId = questions.lastOrNull()?.question_id ?: run {
            Long.MAX_VALUE
        }
        deleteRange(firstId, lastId)
        insert(questions)
    }

    @Query("DELETE FROM question WHERE question_id BETWEEN :firstId AND :lastId")
    fun deleteRange(firstId: Long, lastId: Long)
}
