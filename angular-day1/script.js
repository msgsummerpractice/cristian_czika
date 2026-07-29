let loading = false;

async function getDogImage() {
  const dogImage = document.getElementById('dogImage');
  loading = true;
  dogImage.hidden = false;

  try {
    dogImage.alt = "Loading...";
    const response = await fetch('https://dog.ceo/api/breeds/image/random');
    const data = await response.json();
    dogImage.src = data.message;
    loading = false;
    dogImage.alt = "Random Dog Image";
  } catch (error) {
    console.error('Failed to fetch dog image:', error);
    loading = false;
  }
}