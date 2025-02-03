package com.example.SpringKotlinWebProject

import com.example.SpringKotlinWebProject.model.Question
import com.example.SpringKotlinWebProject.repository.AnswerRepository
import com.example.SpringKotlinWebProject.repository.QuestionRepository
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime

//@SpringBootTest
@SpringBootTest(classes = [SbbApplication::class])
class SbbApplicationTests {

    @Autowired//의존성 강제 주입???
    lateinit var questionRepository: QuestionRepository

    @Autowired
    lateinit var AnswerRepository: AnswerRepository

    //@Transactional rollback 기능이 있어 삽입에는 적합하지 않음.
    @Test
    fun testJpa() {
        for (i in 1..300) {
            val q = Question(
                subject = "테스트 데이터입니다:[%03d]".format(i),
                content = "내용무",
                createDate = LocalDateTime.now(),
                author = null,
            )
            questionRepository.save(q)
        }
    }


    /*fun testJpa() {
        val q1 = Question(
            subject = "sbb가 무엇인가요?",
            content = "sbb에 대해서 알고 싶습니다.",
            createDate = LocalDateTime.now()
        )
        questionRepository.save(q1) // 첫 번째 질문 저장

        val q2 = Question(
            subject = "스프링부트 모델 질문입니다.",
            content = "id는 자동으로 생성되나요?",
            createDate = LocalDateTime.now()
        )
        questionRepository.save(q2) // 두 번째 질문 저장
    } // 저장 (save method)*/

    /*fun testJpa() {
        val all: List<Question> = questionRepository.findAll()
        assertEquals(2, all.size)

        val q: Question = all[0]
        assertEquals("sbb가 무엇인가요?", q.subject)
    }*/ //findAll() method

    /*fun testJpa() {
        val oq: Optional<Question> = questionRepository.findById(8)

        oq.ifPresent{ q->
            assertEquals("id는 자동으로 생성되나요?", q.content)
            assertEquals("id는 자동으로 생성되나요?", q.content)
        }

    }*/ //findById 사용 방법

    /* 그 외 find 관련 method
    findBySubject: 어떤 column으로든 찾기 가능
    findBySubjectAndContent: 두 개 이상의 column으로도 가능
    findBySubjectLike: 문자열중 일부만 같은것도 찾기 가능(그걸로 시작, 그게 있는거, 그걸로 끝나는거 등)
     */


    /*fun testJpa(){
        val oq = questionRepository.findById(7)
        assertTrue(oq.isPresent)

        val q = oq.get()
        q.subject = "수정된 제목"
        questionRepository.save(q)
    } */// 수정 방식(쉽게 말해 가져온다음에 바꾸고, 다시 저장하면 되는듯하다)

    /*fun testJpa(){
        val oq = questionRepository.findById(7)
        assertTrue(oq.isPresent)

        val q = oq.get()
        questionRepository.delete(q);
    }*/ // 삭제 방식(쉽게 말해 가져온다음에 지우기)

    /*fun testJpa() {
        val oq = questionRepository.findById(8)
        assertTrue(oq.isPresent)
        val q:Question = oq.get()

        val a = Answer(
            content = "네 자동으로 생성됩니다",
            question = q,
            createDate = LocalDateTime.now()
        )
        AnswerRepository.save(a) // 첫 번째 질문 저장


    }*/ // 답변 저장 (save method)

    //답변에서 질문찾기는 a.getQuestion()으로 쉽게 가능

    /*fun testJpa() {
        val oq = questionRepository.findById(8)
        assertTrue(oq.isPresent)

        val q = oq.get()
        val answerList = q.answerList //answer 만들때 answerlist 넣어놔서 가져올 수 있음.

        assertEquals(1, answerList.size)
        assertEquals("네 자동으로 생성됩니다", answerList[0].content)
    }*/ //transactional이 필요한 이유: q만 가져오고나면 session이 끊겨서 answerList를 못가져옴.





}