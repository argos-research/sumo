function result = sendReadOneStringCmd(cmdID, varID, objID)
%sendReadOneStringCmd An internal function to build an outgoing message to
%the SUMO server and parse the response.

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: sendReadOneStringCmd.m 20 2015-03-02 16:52:32Z afacostag $

traci.beginMessage(cmdID, varID, objID);
result = traci.checkResult(cmdID, varID, objID);