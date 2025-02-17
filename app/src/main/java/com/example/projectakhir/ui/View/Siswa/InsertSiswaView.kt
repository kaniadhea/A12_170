package com.example.projectakhir.ui.View.Siswa

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectakhir.ui.ViewModel.PenyediaViewModel
import com.example.projectakhir.ui.ViewModel.Siswa.InsertSiswaUiEvent
import com.example.projectakhir.ui.ViewModel.Siswa.InsertSiswaUiState
import com.example.projectakhir.ui.ViewModel.Siswa.InsertSiswaViewModel
import com.example.projectakhir.ui.navigasi.CostumeTopAppBar
import com.example.projectakhir.ui.navigasi.DestinasiNavigasi
import kotlinx.coroutines.launch


object DestinasiEntrySiswa : DestinasiNavigasi {
    override val route = "item_entry_siswa"
    override val titleRes = "Tambah Data Siswa"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntrySwaScreen(
    navigateBack:()->Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertSiswaViewModel = viewModel(factory = PenyediaViewModel.Factory)

){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiEntrySiswa.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        EntryBodySiswa(
            insertSiswaUiState = viewModel.SiswauiState,
            onSiswaValueChange = viewModel::updateInsertSwaState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertSwa()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun EntryBodySiswa(
    insertSiswaUiState: InsertSiswaUiState,
    onSiswaValueChange:(InsertSiswaUiEvent)->Unit,
    onSaveClick:()-> Unit,
    modifier: Modifier = Modifier
){
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInputSiswa(
            insertSiswaUiEvent = insertSiswaUiState.insertSiswaUiEvent,
            onValueChange = onSiswaValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Simpan")
        }
    }

}


@Composable
fun FormInputSiswa(
    insertSiswaUiEvent: InsertSiswaUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertSiswaUiEvent) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = insertSiswaUiEvent.nama_siswa,
            onValueChange = { onValueChange(insertSiswaUiEvent.copy(nama_siswa = it)) },
            label = { Text("Nama Siswa") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        // Ubah id_siswa menjadi Int, menggunakan toString() untuk input
        OutlinedTextField(
            value = insertSiswaUiEvent.id_siswa.toString(),  // Convert to String for TextField
            onValueChange = {
                // Convert kembali ke Int saat nilai diubah
                onValueChange(insertSiswaUiEvent.copy(id_siswa = it.toIntOrNull() ?: 0))
            },
            label = { Text("ID Siswa") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        OutlinedTextField(
            value = insertSiswaUiEvent.email,
            onValueChange = { onValueChange(insertSiswaUiEvent.copy(email = it)) },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        OutlinedTextField(
            value = insertSiswaUiEvent.nomor_telepon,
            onValueChange = { onValueChange(insertSiswaUiEvent.copy(nomor_telepon = it)) },
            label = { Text("Nomor Telepon") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        if (enabled) {
            Text(
                text = "Isi Semua Data!",
                modifier = Modifier.padding(12.dp)
            )
        }

        Divider(
            thickness = 8.dp,
            modifier = Modifier.padding(12.dp)
        )
    }
}

