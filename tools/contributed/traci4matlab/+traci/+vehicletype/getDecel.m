function decel = getDecel(typeID)
%getDecel Returns the maximum deceleration in of vehicles of this type.
%   decel = getDecel(TYPEID) Returns the maximum deceleration in m/s^2 of 
%   vehicles of this type.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getDecel.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
decel = traci.vehicletype.getUniversal(constants.VAR_DECEL, typeID);