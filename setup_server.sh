#!/bin/bash

# Create the directory if it doesn't exist yet.
if [[ ! -d "server" ]]; then
  mkdir server
  cd server || return

  # Download PaperMC 1.16.4-497
  echo "Downloading PaperMC..."
  curl -Lo paper.jar https://papermc.io/api/v2/projects/paper/versions/1.16.5/builds/497/downloads/paper-1.16.5-497.jar

  # Create the run script.
  echo "Making a run script..."
  cat << EOF > run_server.sh
#!/bin/bash

# Go to the server directory.
BASEDIR=\$(dirname "\$0")
cd "\$BASEDIR" || return

java -Xmx2G -Xms2G -jar paper.jar nogui
EOF
  chmod +x run_server.sh

  # Accept the EULA.
  echo "Accepting the EULA..."
  echo "eula=true" > eula.txt

  echo
  echo "Server is successfully created!"
else
  echo "Server is already created. Remove server directory to remake."
fi