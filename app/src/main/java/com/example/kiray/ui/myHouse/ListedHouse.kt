package com.example.kiray.ui.myHouse

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.kiray.common.LocalRole
import com.example.kiray.common.animation.AnimatedComponent
import com.example.kiray.common.animation.Direction
import com.example.kiray.model.House

@Composable
fun ListedHouse(
    house: House,
    onEdit: (House) -> Unit,
    onDelete: (House) -> Unit,
    onClick: (House) -> Unit,
) {
    val isOwner = LocalRole.current
    AnimatedComponent(Direction.BOTTOM_TO_TOP) { offset ->
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .then(Direction.BOTTOM_TO_TOP.modifier(offset)).clickable{
                    onClick(house)
                },
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(6.dp)
        ) {
            Column {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(house.name ?: "", style = MaterialTheme.typography.titleMedium)
                            Spacer(Modifier.height(4.dp))
                            Text(
                                "${house.rooms} • ₹${house.price}/month",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Gray
                            )
                            Spacer(Modifier.height(4.dp))
                            Text(
                                house.address ?: "",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Gray,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }

                        if (isOwner) {
                            // Edit + Delete buttons
                            Row {
                                IconButton(onClick = { onEdit(house) }) {
                                    Icon(Icons.Default.Edit, contentDescription = "Edit")
                                }
                                IconButton(onClick = { onDelete(house) }) {
                                    Icon(
                                        Icons.Default.Delete,
                                        contentDescription = "Delete",
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }
                        }
                    }

                    Spacer(Modifier.height(8.dp))
                    // Amenities row
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        house.amenities?.take(3)?.forEach {
                            AssistChip(onClick = {}, label = { Text(it) })
                        }
                    }
                }
            }
        }
    }
}