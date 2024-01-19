# Compile Commands
BUILDPATH=build
CHAINFLAGS=-d $(BUILDPATH)/
CHAIN=javac
JAVAC=$(CHAIN) $(CHAINFLAGS) 

# Source Files
DATA=src/contato/Data.java
CONTATO=src/contato/Contato.java
ENDERECO=src/contato/Endereco.java
JSONCONTATO=src/contato/JSONContato.java
AGENDAPROXY=src/client/AgendaProxy.java
UDPCLIENT=src/client/UDPClient.java
USER=src/client/User.java
AGENDA=src/server/Agenda.java
AGENDAESQUELETO=src/server/AgendaEsqueleto.java
DESPACHANTE=src/server/Despachante.java
SERVERCONNECTION=src/server/ServerConnection.java
UDPSERVER=src/server/UDPServer.java
MENU=src/client/Menu.java

# Source File Groups
CLIENT=$(AGENDAPROXY) $(UDPCLIENT) $(USER) $(MENU)
SERVER=$(AGENDA) $(AGENDAESQUELETO) $(DESPACHANTE) $(SERVERCONNECTION) $(UDPSERVER) 
CONTATOS=$(CONTATO) $(ENDERECO) $(DATA) $(JSONCONTATO)
UTILITARIO=src/utilitario/Validador.java
JSON=src/org/json/JSONObject.java src/org/json/JSONException.java src/org/json/JSONArray.java src/org/json/JSONTokener.java src/org/json/JSONPointer.java src/org/json/JSONPropertyIgnore.java src/org/json/JSONPropertyName.java src/org/json/JSONPointerException.java src/org/json/JSONWriter.java src/org/json/JSONString.java

# Run Commands
RUN=java
RUNFLAGS=-cp "$(BUILDPATH)"
JAVA=$(RUN) $(RUNFLAGS)

# Executable Files
__CLIENT__=client.User
__SERVER__=server.UDPServer

all:
	@$(JAVAC) $(CONTATOS) $(CLIENT) $(UTILITARIO) $(JSON) $(SERVER) 

run_client:
	@$(JAVA) $(__CLIENT__)  

run_server:
	@$(JAVA) $(__SERVER__)