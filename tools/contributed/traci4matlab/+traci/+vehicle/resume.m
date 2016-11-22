function resume(vehID)
%RESUME Resumes the vehicle from the current stop.
%   RESUME(VEHID) Resumes the vehicle with ID VEHID from the current stop
%   (throws an error if the vehicle is not stopped).

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id$

import traci.constants
global message

traci.beginMessage(constants.CMD_SET_VEHICLE_VARIABLE, constants.CMD_RESUME,...
    vehID, 1 + 4)
message.string = [message.string uint8(sscanf(constants.TYPE_COMPOUND,'%x'))...
    traci.packInt32(0)];
traci.sendExact();