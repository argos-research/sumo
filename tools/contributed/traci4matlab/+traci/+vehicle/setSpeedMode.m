function setSpeedMode(vehID, sm)
%setSpeedMode Sets the vehicle's speed mode as a bitset.
% setSpeedMode(VEHID, SM) Sets the vehicle's speed mode as a bitset. For
%   further details, see 'speed mode' in http://sumo.dlr.de/wiki/TraCI/Change_Vehicle_State

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id$


import traci.constants
traci.sendIntCmd(constants.CMD_SET_VEHICLE_VARIABLE, constants.VAR_SPEEDSETMODE, vehID, sm);






