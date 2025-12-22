// Funciones para eliminar empleados
let currentEmployeeCode = '';
let currentEmployeeName = '';

function openDeleteModal(code, name) {
  currentEmployeeCode = code;
  currentEmployeeName = name;
  
  document.getElementById('deleteEmployeeName').textContent = name;
  document.getElementById('deleteReason').value = '';
  
  const modal = new bootstrap.Modal(document.getElementById('deleteModal'));
  modal.show();
}

async function confirmDelete() {
  const reason = document.getElementById('deleteReason').value;
  const deletedBy = document.getElementById('deletedBy').value;
  
  if (!reason.trim()) {
    alert('Debe escribir una razón de eliminación');
    return;
  }
  
  try {
    // Enviar al backend
    await fetch(`http://localhost:8082/employee/${currentEmployeeCode}`, {
      method: 'DELETE',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        deleteBy: deletedBy,
        reason: reason
      })
    });
    
    alert('✅ Empleado eliminado');
    location.reload(); // Recargar página
    
  } catch (error) {
    console.error('Error eliminando:', error);
    alert('Error al eliminar');
  }
  
  const modal = bootstrap.Modal.getInstance(document.getElementById('deleteModal'));
  if (modal) modal.hide();
}