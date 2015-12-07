function setDecel(typeID, decel)
%setDecel Sets the maximum deceleration for vehicles of this type.
%   setDecel(TYPEID,DECEL) Sets the maximum deceleration in m/s^2 for 
%   vehicles of this type.

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id$

import traci.constants
traci.sendDoubleCmd(constants.CMD_SET_VEHICLETYPE_VARIABLE, constants.VAR_DECEL, typeID, decel);