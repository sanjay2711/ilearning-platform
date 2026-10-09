package com.example.ilearning.ui.screens.course



import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
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
import com.example.ilearning.ui.theme.Purple40
import com.example.ilearning.ui.screens.login.ProgressBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseScreen(
    onCourseClick: (Int) -> Unit,
    courseViewModel: CourseViewModel = hiltViewModel()
) {
    val courseState by courseViewModel.courseState
        .collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Course Dashboard",
                        fontSize = 26.sp,
                        color = Purple40,
                        fontFamily = FontFamily(
                            Font(R.font.google_sans_bold)
                        )
                    )
                }
            )
        }
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFDBD2E8))
        ) {
            when (val state = courseState) {

                is Response.Loading -> {
                    ProgressBar()
                }

                is Response.Success -> {
                    val courses = state.data?.courses.orEmpty()

                    if (courses.isEmpty()) {
                        Text(
                            text = "No courses available",
                            modifier = Modifier.align(Alignment.Center)
                        )
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(
                                items = courses,
                                key = { course -> course.id }
                            ) { course ->
                                CourseItem(
                                    course = course,
                                    onCourseClick = onCourseClick
                                )
                            }
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
fun CourseItem(course: Course, onCourseClick: (Int) -> Unit) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .clickable { onCourseClick(course.id) },
        elevation = CardDefaults.elevatedCardElevation(),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 12.dp)
        ) {
            val (txtName, txtInstructor, progressBar, txtProgress) = createRefs()

            Text(
                modifier = Modifier.constrainAs(txtName) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                },
                text = course.courseName,
                style = TextStyle(
                    fontSize = 18.sp, fontFamily = FontFamily(Font(R.font.google_sans_medium)),
                ),
            )

            Text(
                modifier = Modifier.constrainAs(txtInstructor) {
                    top.linkTo(txtName.bottom, margin = 4.dp)
                    start.linkTo(txtName.start)
                },
                text = course.instructorName,
                style = TextStyle(
                    fontSize = 14.sp, fontFamily = FontFamily(Font(R.font.google_sans_regular)),
                ),
            )

            LinearProgressIndicator(
                progress = { course.progress / 100f },
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(progressBar) {
                        top.linkTo(txtInstructor.bottom, margin = 6.dp)
                        start.linkTo(txtName.start)
                    },
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.constrainAs(txtProgress) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end, margin = 4.dp)
                }
            ) {
                Text(
                    text = course.lessons.count { it.completed }.toString(),
                    fontSize = 18.sp,
                    fontFamily = FontFamily(Font(R.font.google_sans_bold))
                )
                Text(
                    text = "/${course.numberOfLessons}",
                    fontSize = 10.sp,
                    fontFamily = FontFamily(Font(R.font.google_sans_medium))
                )
            }

        }
    }
}

