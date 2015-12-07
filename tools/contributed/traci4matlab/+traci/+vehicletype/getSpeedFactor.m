function speedFactor = getSpeedFactor(typeID)
%getSpeedFactor Returns the chosen speed factor for vehicles of this type.
%   speedFactor = getSpeedFactor(TYPEID) Returns the chosen speed factor for
%   vehicles of this type.

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id$

import traci.constants
speedFactor = traci.vehicletype.getUniversal(constants.VAR_SPEED_FACTOR, typeID);