const swalWithBootstrapButtons = Swal.mixin({
  customClass: {
    confirmButton: "btn btn-success",
    cancelButton: "btn btn-danger",
  },
  buttonsStyling: false,
});
const deletePopUp = (userId)=> {swalWithBootstrapButtons
  .fire({
    title: "Are you sure?",
    text: "You won't be able to revert this!",
    icon: "warning",
    showCancelButton: true,
    confirmButtonText: "Yes, delete it!",
    cancelButtonText: "No, cancel!",
    reverseButtons: true,
  })
  .then((result) => {
    if (result.isConfirmed) {
      deleteUserFunction(userId)
    } else if (
      /* Read more about handling dismissals below */
      result.dismiss === Swal.DismissReason.cancel
    ) {
      swalWithBootstrapButtons.fire(
        "Cancelled",
        "Your imaginary file is safe :)",
        "error"
      );
    }
  });
}
const deleteButtons = document.querySelectorAll(".deleteButton");

const deleteUserFunction = (userId)=>{
    fetch(`delete/${userId}`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json', // Adjust the content type if necessary
          // Add any other headers if needed
        },
      })
        .then((response) => {
            console.log(response.status)
          if (response.status === 200 || response.status == 202 ) {
            // Successful DELETE (status 204)
            swalWithBootstrapButtons.fire(
                "Deleted!",
                "Your file has been deleted.",
                "success"
              ).then((result) => {
                if (result.isConfirmed) {
                    window.location.reload()
                }
              });
              
            console.log('Resource deleted successfully.');
          } else if (response.status === 404) {
            // Resource not found (status 404)
            console.error('Resource not found.');
          } else {
            // Handle other response statuses as needed
            console.error('Failed to delete resource.');
          }
        })
        .catch((error) => {
          console.error('Error:', error);
        });
};

const fireDeletePopUp = () => {
  deleteButtons.forEach((button) => {button.addEventListener(
    "click", (event) => {deletePopUp(event.target.value)}
  
  )})
};


fireDeletePopUp()

