package com.example.projectakhir.ui.View.Instruktur

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectakhir.ui.ViewModel.PenyediaViewModel
import com.example.projectakhir.R
import com.example.projectakhir.model.Instruktur
import com.example.projectakhir.ui.ViewModel.Instruktur.HomeInstrukturUiState
import com.example.projectakhir.ui.ViewModel.Instruktur.HomeInstrukturViewModel
import com.example.projectakhir.ui.navigasi.CostumeTopAppBar
import com.example.projectakhir.ui.navigasi.DestinasiNavigasi


object DestinasiHomeInstruktur: DestinasiNavigasi {
    override val route ="homeinstruktur"
    override val titleRes = "Home Instruktur"
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenInstruktur(
    navigateTolItemEntry: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (Int) -> Unit = {},
    viewModel: HomeInstrukturViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiHomeInstruktur.titleRes,
                canNavigateBack = false,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getIntru()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton (
                onClick = navigateTolItemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Instruktur")
            }
        },
    ) { innerPadding ->
        HomeStatusInstruktur(
            homeUiState = viewModel.instruUiState,
            retryAction = { viewModel.getIntru() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,
            onDeleteClick = {
                viewModel.deleteInstru(it.id_instruktur)
                viewModel.getIntru()
            }
        )
    }
}

@Composable
fun HomeStatusInstruktur(
    homeUiState: HomeInstrukturUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Instruktur) -> Unit = {},
    onDetailClick: (Int) -> Unit
){
    when (homeUiState){
        is HomeInstrukturUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())

        is HomeInstrukturUiState.Success ->
            if (homeUiState.instruktur.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data kontak")
                }
            } else {
                InstruLayout(
                    instruktur = homeUiState.instruktur,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = {
                        onDetailClick(it.id_instruktur.toInt()) // Mengonversi id_instruktur menjadi String
                    },
                    onDeleteClick = {
                        onDeleteClick(it)
                    }
                )
            }
        is HomeInstrukturUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}



@Composable
fun OnLoading(modifier: Modifier = Modifier){
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.gambarloading),
        contentDescription = stringResource(R.string.loading)
    )
}


@Composable
fun OnError(retryAction:() -> Unit, modifier: Modifier = Modifier){
    Column (
        modifier =modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Image(
            painter = painterResource(id = R.drawable.error), contentDescription = ""
        )
        Text(text = stringResource(R.string.loading), modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }

    }
}


@Composable
fun InstruLayout(
    instruktur: List<Instruktur>,
    modifier: Modifier = Modifier,
    onDetailClick:(Instruktur) -> Unit,
    onDeleteClick: (Instruktur) -> Unit = {}
){
    LazyColumn (
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ){
        items(instruktur){ instruktur ->
            InstruCard(
                instruktur = instruktur,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable{onDetailClick(instruktur)},
                onDeleteClick ={
                    onDeleteClick(instruktur)
                }
            )


        }

    }
}



@Composable
fun InstruCard(
    instruktur: Instruktur,
    modifier: Modifier = Modifier,
    onDeleteClick: (Instruktur) -> Unit = {}
){
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = instruktur.nama_instruktur,
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(Modifier.weight(1f))
                IconButton(onClick = { onDeleteClick(instruktur) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                    )
                }

                // Menggunakan toString() untuk mengonversi id_instruktur menjadi String
                Text(
                    text = instruktur.id_instruktur.toString(),  // Mengonversi ke String
                    style = MaterialTheme.typography.titleMedium
                )
            }

            Text(
                text = instruktur.email,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = instruktur.deskripsi,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = instruktur.nomor_telepon,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}
