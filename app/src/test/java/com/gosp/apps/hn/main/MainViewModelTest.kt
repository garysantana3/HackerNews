package com.gosp.apps.hn.main


import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.gosp.apps.hn.data.NewsRepository
import com.gosp.apps.hn.data.database.entities.NewsEntity
import com.gosp.apps.hn.data.models.NewsListResponse
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.invoke
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainViewModelTest{

    @RelaxedMockK
    private lateinit var repo: NewsRepository

    lateinit var getMainViewModel: MainViewModel

    @RelaxedMockK
    private lateinit var context: Context
    @RelaxedMockK
    private lateinit var arrayList: ArrayList<NewsEntity>

    @get:Rule
    var rule:InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun onBefore(){
        MockKAnnotations.init(this)
        getMainViewModel = MainViewModel(repo)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun onAfter(){
        Dispatchers.resetMain()
    }

    @Test
    fun `when database is empty`() = runTest {
        //Given
        coEvery { repo.getAllNewsFromDataBase() } returns null
        //When
        getMainViewModel.getDataInDataBase(context)
        //Then
        coVerify(exactly = 1) { getMainViewModel.getAllNewsFromApi(context) }
    }

    @Test
    fun `when database is not empty`() = runTest {
        //Given
        val myNews = listOf(NewsEntity("1","1","Test 1","https:test.com","garysantana","2022-11-07",false))
        coEvery { repo.getAllNewsFromDataBase() } returns myNews
        //When
        getMainViewModel.getDataInDataBase(context)
        //Then
        coVerify(exactly = 0) { getMainViewModel.getAllNewsFromApi(context) }
    }

    @Test
    fun `when repo return a list set on the livedata`() = runTest {
        //Given
        coEvery { repo.getAllNewsFromDataBase() } returns arrayList
        //When
        getMainViewModel.getAllNews()
        //Then
        assert(getMainViewModel.dataList.value == arrayList )
    }
}


