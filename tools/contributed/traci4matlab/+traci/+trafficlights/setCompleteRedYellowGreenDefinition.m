function setCompleteRedYellowGreenDefinition(tlsID, tls)
%setCompleteRedYellowGreenDefinition Set the complete definition of the traffic light.
%   setCompleteRedYellowGreenDefinition(TLSID,TLS) Sets the attributes of
%   trafic lights' definition including all the phase definitions. Those
%   attributes are included in the TLS parameter, which is a
%   traci.trafficlights.Logic object.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: setCompleteRedYellowGreenDefinition.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
global message
len = 1+4 + 1+4+length(tls.subID) + 1+4 + 1+4 + 1+4 + 1+4; % tls parameter
itemNo = 1+1+1+1+1;
for p=1:length(tls.phases)
    len = len + 1+4 + 1+4 + 1+4 + 1+4+length(tls.phases{p}.phaseDef);
    itemNo = itemNo + 4;
end
traci.beginMessage(constants.CMD_SET_TL_VARIABLE, constants.TL_COMPLETE_PROGRAM_RYG, tlsID, len);
message.string = [message.string uint8(sscanf(constants.TYPE_COMPOUND,'%x')) ...
    traci.packInt32(itemNo)];
message.string = [message.string uint8(sscanf(constants.TYPE_STRING,'%x')) ...
    traci.packInt32(length(tls.subID)) uint8(tls.subID)];   % Program ID
message.string = [message.string uint8(sscanf(constants.TYPE_INTEGER,'%x')) ...
    traci.packInt32(0)];    %   Type
message.string = [message.string uint8(sscanf(constants.TYPE_COMPOUND,'%x')) ...
    traci.packInt32(0)];    % subitems
message.string = [message.string uint8(sscanf(constants.TYPE_INTEGER,'%x')) ...
    traci.packInt32(tls.currentPhaseIndex)];
message.string = [message.string uint8(sscanf(constants.TYPE_INTEGER,'%x')) ...
    traci.packInt32(length(tls.phases))];   % phaseNo

for i=1:length(tls.phases)
    message.string = [message.string uint8(sscanf(constants.TYPE_INTEGER,'%x')) ...
        traci.packInt32(tls.phases{i}.duration) ...
        uint8(sscanf(constants.TYPE_INTEGER,'%x')) traci.packInt32(tls.phases{i}.duration1) ...
        uint8(sscanf(constants.TYPE_INTEGER,'%x')) traci.packInt32(tls.phases{i}.duration2)];
    message.string = [message.string uint8(sscanf(constants.TYPE_STRING,'%x')) ...
        traci.packInt32(length(tls.phases{i}.phaseDef)) ...
        uint8(tls.phases{i}.phaseDef)];
end
traci.sendExact();