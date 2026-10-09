package com.example.ilearning.ui.screens.courseDetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material.icons.filled.Pending
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ilearning.R
import com.example.ilearning.data.remote.Response
import com.example.ilearning.domain.models.Course
import com.example.ilearning.domain.models.Lesson
import com.example.ilearning.ui.screens.login.ProgressBar
import com.example.ilearning.ui.theme.Purple40
import com.example.ilearning.ui.theme.Purple80


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseDetailScreen(
    id : Int,
    courseDetailViewModel: CourseDetailViewModel = hiltViewModel(),
    onBackPressed : () -> Unit
) {

    val courseDetailState by courseDetailViewModel.courseDetailState
        .collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        courseDetailViewModel.getCourseById(id)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        IconButton(onClick = { onBackPressed() }) {
                            Icon(
                                Icons.Default.ArrowBack,
                                tint = Color.Black,
                                contentDescription = ""
                            )
                        }
                        Text(
                            text = "Course Details",
                            fontSize = 26.sp,
                            color = Purple40,
                            fontFamily = FontFamily(
                                Font(R.font.google_sans_bold)
                            )
                        )
                    }
                },

                )
        }
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFDBD2E8))
        ){

            when (val state = courseDetailState) {

                is Response.Loading -> {
                    ProgressBar()
                }

                is Response.Success -> {
                    state.data?.let {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Top
                        ) {
                            CourseProgressCard(it)
                            CourseHeadingCard(it.lessons)
                        }
                    }

                }

                is Response.Error -> {
                    Text(
                        text = "Failed to load courses",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                is Response.Empty -> {
                    Text(
                        text = "No courses available",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }

        }

    }
}


@Composable
fun CourseProgressCard(course: Course)
{
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.elevatedCardElevation(),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            val (txtName, txtInstructor, progressBar, divider) = createRefs()

            Text(
                modifier = Modifier.constrainAs(txtName) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                },
                text = course.courseName,
                style = TextStyle(
                    fontSize = 18.sp, fontFamily = FontFamily(Font(R.font.google_sans_bold)),
                ),
            )

            Text(
                modifier = Modifier.constrainAs(txtInstructor) {
                    top.linkTo(txtName.bottom, margin = 4.dp)
                    start.linkTo(txtName.start)
                },
                text = course.instructorName,
                style = TextStyle(
                    fontSize = 14.sp, fontFamily = FontFamily(Font(R.font.google_sans_medium)),
                ),
            )

            Box(
                modifier = Modifier
                    .size(100.dp)
                    .constrainAs(
                        progressBar
                    ){
                        top.linkTo(txtInstructor.bottom,8.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                contentAlignment = Alignment.Center
            ) {

                CircularProgressIndicator(
                    progress = {course.progress / 100f},
                    modifier = Modifier.fillMaxSize(),
                    strokeWidth = 6.dp
                )
                Text(
                    text = "${course.progress}%",
                    fontSize = 18.sp,
                    fontFamily = FontFamily(Font(R.font.google_sans_bold))
                )

            }

        }
    }
}


@Composable
fun CourseHeadingCard(lessons : List<Lesson>)
{
    LazyColumn (
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, start = 8.dp, end = 8.dp)
    ) {

        items(lessons.count()){ it ->
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                HeadingItem(lessons[it])
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    color = Purple80,
                    thickness = 1.dp
                )
            }

        }

    }
}


@Composable
fun HeadingItem(lesson: Lesson)
{
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = lesson.lessonName,
            style = TextStyle(
                fontSize = 16.sp, fontFamily = FontFamily(Font(R.font.google_sans_medium)),
            ),
        )

        Box(
            modifier = Modifier.size(30.dp),
            contentAlignment = Alignment.Center
        ) {

            if(lesson.completed) {
                Icon(
                     Icons.Default.DoneAll,
                    tint = Purple40,
                    contentDescription = ""
                )
            } else {
                Icon(
                    Icons.Default.Pending,
                    tint = Color.Black,
                    contentDescription = ""
                )
            }

        }


    }
}

