function imperfection = getImperfection(typeID)
%getImperfection Returns the driver's imperfection of vehicles of this type.
%   imperfection = getImperfection(TYPEID) Returns the driver's imperfection
%   (dawdling) [0,1] of vehicles of this type.

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id$

import traci.constants
imperfection = traci.vehicletype.getUniversal(constants.VAR_IMPERFECTION, typeID);